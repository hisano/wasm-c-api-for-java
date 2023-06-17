package jp.hisano.wasm_c_api.jna;

import java.io.File;
import java.io.IOException;

import com.sun.jna.Native;
import com.sun.jna.Platform;

public final class Library {
	public static WasmCApi load(Engine engine) {
		try {
			String name = engine.name().toLowerCase();
			String mappedName = System.mapLibraryName(name).replace(".dylib", ".jnilib");
			String libName = "/jp/hisano/wasm_c_api/runtime/" + name + "/" + Platform.RESOURCE_PREFIX + "/" + mappedName;
			File lib = Native.extractFromResourcePath(libName, Native.class.getClassLoader());
			if (lib == null) {
				throw new UnsatisfiedLinkError("Could not find '" + Platform.RESOURCE_PREFIX + "/" + mappedName + "'");
			}
			System.load(lib.getAbsolutePath());
			return Native.load(name, WasmCApi.class);
		} catch (IOException e) {
			throw new UnsatisfiedLinkError(e.getMessage());
		}
	}

	public enum Engine {
		WASMTIME,
		WASMER,
	}

	private Library() {
	}
}
