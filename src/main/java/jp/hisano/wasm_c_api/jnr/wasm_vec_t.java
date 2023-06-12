package jp.hisano.wasm_c_api.jnr;

import jnr.ffi.Runtime;
import jnr.ffi.Struct;

abstract class wasm_vec_t extends Struct {
	public final size_t size = new size_t();
	public final Pointer data = new Pointer();

	wasm_vec_t(Runtime runtime) {
		super(runtime);
	}
}
