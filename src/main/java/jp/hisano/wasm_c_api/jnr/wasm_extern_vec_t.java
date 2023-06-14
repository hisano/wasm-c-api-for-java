package jp.hisano.wasm_c_api.jnr;

import jnr.ffi.Runtime;

public class wasm_extern_vec_t extends wasm_vec_t<wasm_extern_t_pointer> {
	public wasm_extern_vec_t(Runtime runtime) {
		super(runtime);
	}

	public wasm_extern_vec_t(Runtime runtime, wasm_extern_t_pointer... rawData) {
		super(runtime, rawData);
	}

	@Override
	wasm_extern_t_pointer toData(jnr.ffi.Pointer pointer) {
		return new wasm_extern_t_pointer(pointer);
	}

	public wasm_extern_vec_t_pointer toPointer() {
		return new wasm_extern_vec_t_pointer(this);
	}
}
