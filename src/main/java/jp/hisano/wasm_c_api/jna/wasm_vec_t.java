package jp.hisano.wasm_c_api.jna;

import java.util.Arrays;
import java.util.List;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;

public abstract class wasm_vec_t extends Structure {
	public size_t size;
	public Pointer data;

	@Override
	protected List<String> getFieldOrder() {
		return Arrays.asList("size", "data");
	}
}
