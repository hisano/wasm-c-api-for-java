package jp.hisano.wasm_c_api.jnr;

import jnr.ffi.Runtime;

public class wasm_valtype_vec_t extends wasm_vec_t {
	public wasm_valtype_vec_t(Runtime runtime) {
		super(runtime);
	}

	@Override
	wasm_t_pointer toData(jnr.ffi.Pointer pointer) {
		return null;
	}
}
