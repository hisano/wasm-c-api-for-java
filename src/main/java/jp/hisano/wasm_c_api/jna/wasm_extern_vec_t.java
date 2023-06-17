package jp.hisano.wasm_c_api.jna;

import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.unix.LibCAPI.size_t;

public final class wasm_extern_vec_t extends wasm_vec_t {
	public wasm_extern_vec_t() {
	}

	public wasm_extern_vec_t(wasm_extern_t_pointer[] externs) {
		this.size = new size_t(externs.length);

		Pointer data = new Memory(Native.POINTER_SIZE * externs.length);
		for (int i = 0; i < externs.length; i++) {
			data.setPointer(i * Native.POINTER_SIZE, externs[i].getPointer());
		}
		this.data = data;
	}

	public wasm_extern_t_pointer getData(int index) {
		return new wasm_extern_t_pointer(this.data.getPointer(index * Native.POINTER_SIZE));
	}
}
