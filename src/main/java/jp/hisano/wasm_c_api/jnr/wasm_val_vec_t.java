package jp.hisano.wasm_c_api.jnr;

import jnr.ffi.Runtime;

public class wasm_val_vec_t extends wasm_vec_t<wasm_val_t_pointer> {
	public wasm_val_vec_t(Runtime runtime) {
		super(runtime);
	}

	@Override
	wasm_val_t_pointer toData(jnr.ffi.Pointer pointer) {
		return new wasm_val_t_pointer(pointer);
	}

	public wasm_val_vec_t_pointer toPointer() {
		return new wasm_val_vec_t_pointer(this);
	}
}
