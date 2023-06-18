package jp.hisano.wasm_c_api.jna;

import com.sun.jna.IntegerType;
import com.sun.jna.Native;

public final class size_t extends IntegerType {
	public size_t() {
		this(0);
	}

	public size_t(long value) {
		super(Native.SIZE_T_SIZE, value, true);
	}
}
