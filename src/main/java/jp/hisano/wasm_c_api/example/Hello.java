package jp.hisano.wasm_c_api.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import jnr.ffi.LibraryLoader;
import jnr.ffi.Pointer;
import jnr.ffi.Runtime;
import jp.hisano.wasm_c_api.jnr.WasmCApi;
import jp.hisano.wasm_c_api.jnr.wasm_byte_vec_t;
import jp.hisano.wasm_c_api.jnr.wasm_engine_t_pointer;
import jp.hisano.wasm_c_api.jnr.wasm_extern_t_pointer;
import jp.hisano.wasm_c_api.jnr.wasm_extern_vec_t;
import jp.hisano.wasm_c_api.jnr.wasm_func_t_pointer;
import jp.hisano.wasm_c_api.jnr.wasm_functype_t_pointer;
import jp.hisano.wasm_c_api.jnr.wasm_instance_t_pointer;
import jp.hisano.wasm_c_api.jnr.wasm_module_t_pointer;
import jp.hisano.wasm_c_api.jnr.wasm_store_t_pointer;
import jp.hisano.wasm_c_api.jnr.wasm_val_vec_t;

public class Hello {
	public static void main(String[] arguments) throws IOException {
		WasmCApi api = LibraryLoader.create(WasmCApi.class).library("wasmtime").load();
		Runtime runtime = Runtime.getRuntime(api);

		/*
  // Initialize.
  printf("Initializing...\n");
  wasm_engine_t* engine = wasm_engine_new();
  wasm_store_t* store = wasm_store_new(engine);
		 */
		System.out.printf("Initializing...\n");
		wasm_engine_t_pointer engine = api.wasm_engine_new();
		wasm_store_t_pointer store = api.wasm_store_new(engine);
		/*
  // Load binary.
  printf("Loading binary...\n");
  FILE* file = fopen("hello.wasm", "rb");
  if (!file) {
    printf("> Error loading module!\n");
    return 1;
  }
  fseek(file, 0L, SEEK_END);
  size_t file_size = ftell(file);
  fseek(file, 0L, SEEK_SET);
  wasm_byte_vec_t binary;
  wasm_byte_vec_new_uninitialized(&binary, file_size);
  if (fread(binary.data, file_size, 1, file) != 1) {
    printf("> Error loading module!\n");
    return 1;
  }
  fclose(file);
		 */
		System.out.printf("Loading binary...\n");
		File file = new File("hello.wasm");
		byte[] fileContent = Files.readAllBytes(file.toPath());
		wasm_byte_vec_t binary = new wasm_byte_vec_t(runtime);
		api.wasm_byte_vec_new_uninitialized(binary.toPointer(), file.length());
		binary.data.get().put(0, fileContent, 0, fileContent.length);
		/*
  // Validate.
  printf("Validating module...\n");
  if (!wasm_module_validate(store, &binary)) {
    printf("> Error validating module!\n");
    return 1;
  }
		 */
		System.out.printf("Validating module...\n");
		if (!api.wasm_module_validate(store, binary.toPointer())) {
			System.out.printf("> Error validating module!\n");
			System.exit(1);
		}
		/*
  // Compile.
  printf("Compiling module...\n");
  own wasm_module_t* module = wasm_module_new(store, &binary);
  if (!module) {
    printf("> Error compiling module!\n");
    return 1;
  }
		 */
		System.out.printf("Compiling module...\n");
		wasm_module_t_pointer module = api.wasm_module_new(store, binary.toPointer());
		if (module.getPointer().address() == 0) {
			System.out.printf("> Error compiling module!\n");
			System.exit(1);
		}
		/*
  wasm_byte_vec_delete(&binary);
		*/
		api.wasm_byte_vec_delete(binary.toPointer());
		/*
  // Create external print functions.
  printf("Creating callback...\n");
  own wasm_functype_t* hello_type = wasm_functype_new_0_0();
  own wasm_func_t* hello_func =
    wasm_func_new(store, hello_type, hello_callback);
		 */
		System.out.printf("Creating callback...\n");
		wasm_functype_t_pointer hello_type = api.wasm_functype_new_0_0();
		wasm_func_t_pointer hello_func = api.wasm_func_new(store, hello_type, (args, results) -> {
			System.out.printf("Calling back...\n");
			System.out.printf("> Hello World!\n");
			return null;
		});
		/*
  wasm_functype_delete(hello_type);
		 */
		api.wasm_functype_delete(hello_type);
		/*
  // Instantiate.
  printf("Instantiating module...\n");
  wasm_extern_t* externs[] = { wasm_func_as_extern(hello_func) };
  wasm_extern_vec_t imports = WASM_ARRAY_VEC(externs);
  own wasm_instance_t* instance =
    wasm_instance_new(store, module, &imports, NULL);
  if (!instance) {
    printf("> Error instantiating module!\n");
    return 1;
  }
		 */
		System.out.printf("Instantiating module...\n");
		wasm_extern_t_pointer[] externs = new wasm_extern_t_pointer[] { api.wasm_func_as_extern(hello_func) };
		wasm_extern_vec_t imports = new wasm_extern_vec_t(runtime, externs);
		wasm_instance_t_pointer instance = api.wasm_instance_new(store, module, imports, null);
		if (instance == null) {
			System.out.printf("> Error instantiating module!\n");
			System.exit(1);
		}
		/*
  wasm_func_delete(hello_func);
		 */
		api.wasm_func_delete(hello_func);
		/*
  // Extract export.
  printf("Extracting export...\n");
  own wasm_extern_vec_t exports;
  wasm_instance_exports(instance, &exports);
  if (exports.size == 0) {
    printf("> Error accessing exports!\n");
    return 1;
  }
  const wasm_func_t* run_func = wasm_extern_as_func(exports.data[0]);
  if (run_func == NULL) {
    printf("> Error accessing export!\n");
    return 1;
  }
		 */
		System.out.printf("Extracting export...\n");
		wasm_extern_vec_t exports = new wasm_extern_vec_t(runtime);
		api.wasm_instance_exports(instance, exports);
		if (exports.size.get() == 0) {
			System.out.printf("> Error accessing exports!\n");
			System.exit(1);
		}
		Pointer run_func = api.wasm_extern_as_func(exports.data.get().getPointer(0));
		if (run_func.address() == 0) {
			System.out.printf("> Error accessing export!\n");
			System.exit(1);
		}
		/*
  wasm_module_delete(module);
  wasm_instance_delete(instance);
		 */
		api.wasm_module_delete(module);
		api.wasm_instance_delete(instance);
		/*
  // Call.
  printf("Calling export...\n");
  wasm_val_vec_t args = WASM_EMPTY_VEC;
  wasm_val_vec_t results = WASM_EMPTY_VEC;
  if (wasm_func_call(run_func, &args, &results)) {
    printf("> Error calling function!\n");
    return 1;
  }
		 */
		System.out.printf("Calling export...\n");
		wasm_val_vec_t WASM_EMPTY_VEC = new wasm_val_vec_t(runtime);
		WASM_EMPTY_VEC.size.set(0);
		WASM_EMPTY_VEC.data.set((Pointer) null);
		if (api.wasm_func_call(run_func, WASM_EMPTY_VEC, WASM_EMPTY_VEC) != null) {
			System.out.printf("> Error calling function!\n");
			System.exit(1);
		}
		/*
  wasm_extern_vec_delete(&exports);
		 */
		api.wasm_extern_vec_delete(exports.toPointer());
		/*
  // Shut down.
  printf("Shutting down...\n");
  wasm_store_delete(store);
  wasm_engine_delete(engine);
		 */
		System.out.printf("Shutting down...\n");
		api.wasm_store_delete(store);
		api.wasm_engine_delete(engine);
		/*
  // All done.
  printf("Done.\n");
  return 0;
  		 */
		System.out.printf("Done.\n");
	}
}
