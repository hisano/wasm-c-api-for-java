package jp.hisano.wasm_c_api.jnr;

import jnr.ffi.Pointer;
import jnr.ffi.mapper.FromNativeContext;
import jnr.ffi.mapper.FromNativeConverter.FromNative;
import jnr.ffi.mapper.ToNativeContext;
import jnr.ffi.mapper.ToNativeConverter.ToNative;

public final class wasm_module_t_pointer {
	@FromNative(nativeType = Pointer.class)
	public static wasm_module_t_pointer fromNative(Pointer value, FromNativeContext context) {
		return value != null ? new wasm_module_t_pointer(value) : null;
	}

	@ToNative(nativeType = Pointer.class)
	public static Pointer toNative(wasm_module_t_pointer value, ToNativeContext context) {
		return value != null ? value._pointer : null;
	}

	private final Pointer _pointer;

	private wasm_module_t_pointer(Pointer pointer) {
		_pointer = pointer;
	}

	public Pointer getPointer() {
		return _pointer;
	}
}
