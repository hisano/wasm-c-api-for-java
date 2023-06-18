package jp.hisano.wasm_c_api.jna.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import jp.hisano.wasm_c_api.jna.Library;
import static jp.hisano.wasm_c_api.jna.Library.Engine.*;
import jp.hisano.wasm_c_api.jna.WasmCApi;
import jp.hisano.wasm_c_api.jna.size_t;
import jp.hisano.wasm_c_api.jna.wasm_engine_t_pointer;
import jp.hisano.wasm_c_api.jna.wasm_store_t_pointer;
import jp.hisano.wasm_c_api.jna.wasm_byte_vec_t;
import jp.hisano.wasm_c_api.jna.wasm_module_t_pointer;
import jp.hisano.wasm_c_api.jna.wasm_func_t_pointer;
import jp.hisano.wasm_c_api.jna.wasm_functype_t_pointer;
import jp.hisano.wasm_c_api.jna.wasm_extern_t_pointer;
import jp.hisano.wasm_c_api.jna.wasm_extern_vec_t;
import jp.hisano.wasm_c_api.jna.wasm_instance_t_pointer;
import jp.hisano.wasm_c_api.jna.wasm_val_vec_t;

public class Hello {
	public static void main(String[] arguments) throws IOException {
		WasmCApi api = Library.load(WASMER);

		System.out.printf("Initializing...\n");
		wasm_engine_t_pointer engine = api.wasm_engine_new();
		wasm_store_t_pointer store = api.wasm_store_new(engine);

		System.out.printf("Loading binary...\n");
		File file = new File("hello.wasm");
		byte[] fileContent = Files.readAllBytes(file.toPath());
		wasm_byte_vec_t binary = new wasm_byte_vec_t();
		api.wasm_byte_vec_new_uninitialized(binary, new size_t(file.length()));
		binary.data.write(0, fileContent, 0, fileContent.length);

		System.out.printf("Validating module...\n");
		if (!api.wasm_module_validate(store, binary)) {
			System.out.printf("> Error validating module!\n");
			System.exit(1);
		}

		System.out.printf("Compiling module...\n");
		wasm_module_t_pointer module = api.wasm_module_new(store, binary);
		if (module == null) {
			System.out.printf("> Error compiling module!\n");
			System.exit(1);
		}

		api.wasm_byte_vec_delete(binary);

		System.out.printf("Creating callback...\n");
		wasm_functype_t_pointer hello_type = api.wasm_functype_new_0_0();
		wasm_func_t_pointer hello_func = api.wasm_func_new(store, hello_type, (args, results) -> {
			System.out.printf("Calling back...\n");
			System.out.printf("> Hello World!\n");
			return null;
		});

		api.wasm_functype_delete(hello_type);

		System.out.printf("Instantiating module...\n");
		wasm_extern_t_pointer[] externs = new wasm_extern_t_pointer[] { api.wasm_func_as_extern(hello_func) };
		wasm_extern_vec_t imports = new wasm_extern_vec_t(externs);
		wasm_instance_t_pointer instance = api.wasm_instance_new(store, module, imports, null);
		if (instance == null) {
			System.out.printf("> Error instantiating module!\n");
			System.exit(1);
		}

		api.wasm_func_delete(hello_func);

		System.out.printf("Extracting export...\n");
		wasm_extern_vec_t exports = new wasm_extern_vec_t();
		api.wasm_instance_exports(instance, exports);
		if (exports.size.intValue() == 0) {
			System.out.printf("> Error accessing exports!\n");
			System.exit(1);
		}
		wasm_func_t_pointer run_func = api.wasm_extern_as_func(exports.getDataElement(0));
		if (run_func == null) {
			System.out.printf("> Error accessing export!\n");
			System.exit(1);
		}

		api.wasm_module_delete(module);
		api.wasm_instance_delete(instance);

		System.out.printf("Calling export...\n");
		wasm_val_vec_t WASM_EMPTY_VEC = new wasm_val_vec_t();
		if (api.wasm_func_call(run_func, WASM_EMPTY_VEC, WASM_EMPTY_VEC) != null) {
			System.out.printf("> Error calling function!\n");
			System.exit(1);
		}

		api.wasm_extern_vec_delete(exports);

		System.out.printf("Shutting down...\n");
		api.wasm_store_delete(store);
		api.wasm_engine_delete(engine);

		System.out.printf("Done.\n");
	}
}
