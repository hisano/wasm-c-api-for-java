package jp.hisano.wasm_c_api.jnr;

import jnr.ffi.Runtime;

public class wasm_extern_vec_t extends wasm_vec_t {
	public wasm_extern_vec_t(Runtime runtime) {
		super(runtime);
	}
}
