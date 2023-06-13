package jp.hisano.wasm_c_api.jnr;

import jnr.ffi.Memory;
import jnr.ffi.Runtime;
import jnr.ffi.Struct;

abstract class wasm_vec_t<T extends wasm_t_pointer> extends Struct {
	public final size_t size = new size_t();
	public final Pointer data = new Pointer();

	private final T[] _rawData;

	wasm_vec_t(Runtime runtime) {
		super(runtime);

		_rawData = null;
	}

	wasm_vec_t(Runtime runtime, T... rawData) {
		super(runtime);

		if (rawData.length != 0) {
			int pointerSize = runtime.addressSize();
			jnr.ffi.Pointer array = Memory.allocateDirect(runtime, rawData.length * pointerSize);
			for (int i = 0; i < rawData.length; i++) {
				array.putPointer(i * pointerSize, rawData[i].getPointer());
			}
			size.set(rawData.length);
			data.set(array);
		}

		_rawData = rawData;
	}

	public T getData(int index) {
		if (_rawData != null) {
			return _rawData[index];
		}
		return toData(data.get().getPointer(index * getRuntime().addressSize()));
	}

	abstract T toData(jnr.ffi.Pointer pointer);
}
