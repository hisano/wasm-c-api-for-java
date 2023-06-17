package jp.hisano.wasm_c_api.jna;

import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.PointerType;
import com.sun.jna.platform.unix.LibCAPI;

public abstract class wasm_pointer_vec_t<T extends PointerType> extends wasm_vec_t {
	public wasm_pointer_vec_t() {
	}

	public wasm_pointer_vec_t(T[] dataElements) {
		this.size = new LibCAPI.size_t(dataElements.length);

		Pointer data = new Memory(Native.POINTER_SIZE * dataElements.length);
		for (int i = 0; i < dataElements.length; i++) {
			data.setPointer(i * Native.POINTER_SIZE, dataElements[i].getPointer());
		}
		this.data = data;
	}

	public T getDataElement(int index) {
		return toDataElement(this.data.getPointer(index * Native.POINTER_SIZE));
	}

	abstract T toDataElement(Pointer pointer);
}
