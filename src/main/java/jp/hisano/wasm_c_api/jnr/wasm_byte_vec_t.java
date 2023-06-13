package jp.hisano.wasm_c_api.jnr;

import jnr.ffi.Runtime;
import static jp.hisano.wasm_c_api.jnr.wasm_byte_vec_t_pointer.*;

public final class wasm_byte_vec_t extends wasm_vec_t {
	public wasm_byte_vec_t(Runtime runtime) {
		super(runtime);
	}

	@Override
	wasm_t_pointer toData(jnr.ffi.Pointer pointer) {
		return null;
	}

	public wasm_byte_vec_t_pointer toPointer() {
		return createFrom(this);
	}
}
