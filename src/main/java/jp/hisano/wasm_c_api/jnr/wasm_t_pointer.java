package jp.hisano.wasm_c_api.jnr;

import jnr.ffi.Pointer;

abstract class wasm_t_pointer {
	private final Pointer _pointer;

	wasm_t_pointer(Pointer pointer) {
		_pointer = pointer;
	}

	public Pointer getPointer() {
		return _pointer;
	}
}
