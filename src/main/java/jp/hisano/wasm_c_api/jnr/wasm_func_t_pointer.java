package jp.hisano.wasm_c_api.jnr;

import jnr.ffi.Pointer;
import jnr.ffi.mapper.FromNativeContext;
import jnr.ffi.mapper.FromNativeConverter.FromNative;
import jnr.ffi.mapper.ToNativeContext;
import jnr.ffi.mapper.ToNativeConverter.ToNative;

public final class wasm_func_t_pointer extends wasm_t_pointer {
	@FromNative(nativeType = Pointer.class)
	public static wasm_func_t_pointer fromNative(Pointer value, FromNativeContext context) {
		return value != null ? new wasm_func_t_pointer(value) : null;
	}

	@ToNative(nativeType = Pointer.class)
	public static Pointer toNative(wasm_func_t_pointer value, ToNativeContext context) {
		return value != null ? value.getPointer() : null;
	}

	wasm_func_t_pointer(Pointer pointer) {
		super(pointer);
	}
}
