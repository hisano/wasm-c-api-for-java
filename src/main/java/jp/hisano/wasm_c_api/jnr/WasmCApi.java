package jp.hisano.wasm_c_api.jnr;

import jnr.ffi.Pointer;
import jnr.ffi.Runtime;
import jnr.ffi.annotations.Delegate;
import jnr.ffi.types.size_t;

public interface WasmCApi {
	/*
// Engine

WASM_DECLARE_OWN(engine)

WASM_API_EXTERN own wasm_engine_t* wasm_engine_new(void);
WASM_API_EXTERN own wasm_engine_t* wasm_engine_new_with_config(own wasm_config_t*);
	 */
	void wasm_engine_delete(wasm_engine_t_pointer enginePointer);

	wasm_engine_t_pointer wasm_engine_new();

	/*
// Store

WASM_DECLARE_OWN(store)

WASM_API_EXTERN own wasm_store_t* wasm_store_new(wasm_engine_t*);
	 */
	void wasm_store_delete(wasm_store_t_pointer storePointer);

	wasm_store_t_pointer wasm_store_new(wasm_engine_t_pointer enginePointer);

	/*
// Byte vectors

typedef byte_t wasm_byte_t;
WASM_DECLARE_VEC(byte, )
	 */
	void wasm_byte_vec_delete(wasm_byte_vec_t_pointer out);

	void wasm_byte_vec_new_uninitialized(wasm_byte_vec_t_pointer out, @size_t long size);

	/*
// Modules

WASM_DECLARE_SHARABLE_REF(module)

WASM_API_EXTERN own wasm_module_t* wasm_module_new(
  wasm_store_t*, const wasm_byte_vec_t* binary);

WASM_API_EXTERN bool wasm_module_validate(wasm_store_t*, const wasm_byte_vec_t* binary);

WASM_API_EXTERN void wasm_module_imports(const wasm_module_t*, own wasm_importtype_vec_t* out);
WASM_API_EXTERN void wasm_module_exports(const wasm_module_t*, own wasm_exporttype_vec_t* out);

WASM_API_EXTERN void wasm_module_serialize(const wasm_module_t*, own wasm_byte_vec_t* out);
WASM_API_EXTERN own wasm_module_t* wasm_module_deserialize(wasm_store_t*, const wasm_byte_vec_t*);
	 */
	void wasm_module_delete(Pointer module);

	Pointer wasm_module_new(wasm_store_t_pointer storePointer, wasm_byte_vec_t_pointer binary);

	boolean wasm_module_validate(wasm_store_t_pointer storePointer, wasm_byte_vec_t_pointer binary);

	/*
// Function Instances

WASM_DECLARE_REF(func)

typedef own wasm_trap_t* (*wasm_func_callback_t)(
  const wasm_val_vec_t* args, own wasm_val_vec_t* results);
typedef own wasm_trap_t* (*wasm_func_callback_with_env_t)(
  void* env, const wasm_val_vec_t* args, wasm_val_vec_t* results);

WASM_API_EXTERN own wasm_func_t* wasm_func_new(
  wasm_store_t*, const wasm_functype_t*, wasm_func_callback_t);
WASM_API_EXTERN own wasm_func_t* wasm_func_new_with_env(
  wasm_store_t*, const wasm_functype_t* type, wasm_func_callback_with_env_t,
  void* env, void (*finalizer)(void*));

WASM_API_EXTERN own wasm_functype_t* wasm_func_type(const wasm_func_t*);
WASM_API_EXTERN size_t wasm_func_param_arity(const wasm_func_t*);
WASM_API_EXTERN size_t wasm_func_result_arity(const wasm_func_t*);

WASM_API_EXTERN own wasm_trap_t* wasm_func_call(
  const wasm_func_t*, const wasm_val_vec_t* args, wasm_val_vec_t* results);
	 */
	Pointer wasm_func_new(wasm_store_t_pointer storePointer, Pointer functype, wasm_func_callback_t callback);
	void wasm_func_delete(Pointer func);

	@FunctionalInterface
	interface wasm_func_callback_t {
		@Delegate
		Pointer call(Pointer args, Pointer results);
	}

	Pointer wasm_func_call(Pointer funcPointer, wasm_val_vec_t args, wasm_val_vec_t results);
	/*

// Value Types

WASM_DECLARE_TYPE(valtype)
	 */
	void wasm_valtype_vec_new_empty(wasm_valtype_vec_t out);

	/*
// Function Types

WASM_DECLARE_TYPE(functype)

WASM_API_EXTERN own wasm_functype_t* wasm_functype_new(
  own wasm_valtype_vec_t* params, own wasm_valtype_vec_t* results);

WASM_API_EXTERN const wasm_valtype_vec_t* wasm_functype_params(const wasm_functype_t*);
WASM_API_EXTERN const wasm_valtype_vec_t* wasm_functype_results(const wasm_functype_t*);
	 */
	void wasm_functype_delete(Pointer functype);
	Pointer wasm_functype_new(wasm_valtype_vec_t params, wasm_valtype_vec_t results);

	/*
// Externals

WASM_DECLARE_REF(extern)
WASM_DECLARE_VEC(extern, *)

WASM_API_EXTERN wasm_externkind_t wasm_extern_kind(const wasm_extern_t*);
WASM_API_EXTERN own wasm_externtype_t* wasm_extern_type(const wasm_extern_t*);

WASM_API_EXTERN wasm_extern_t* wasm_func_as_extern(wasm_func_t*);
WASM_API_EXTERN wasm_extern_t* wasm_global_as_extern(wasm_global_t*);
WASM_API_EXTERN wasm_extern_t* wasm_table_as_extern(wasm_table_t*);
WASM_API_EXTERN wasm_extern_t* wasm_memory_as_extern(wasm_memory_t*);

WASM_API_EXTERN wasm_func_t* wasm_extern_as_func(wasm_extern_t*);
WASM_API_EXTERN wasm_global_t* wasm_extern_as_global(wasm_extern_t*);
WASM_API_EXTERN wasm_table_t* wasm_extern_as_table(wasm_extern_t*);
WASM_API_EXTERN wasm_memory_t* wasm_extern_as_memory(wasm_extern_t*);

WASM_API_EXTERN const wasm_extern_t* wasm_func_as_extern_const(const wasm_func_t*);
WASM_API_EXTERN const wasm_extern_t* wasm_global_as_extern_const(const wasm_global_t*);
WASM_API_EXTERN const wasm_extern_t* wasm_table_as_extern_const(const wasm_table_t*);
WASM_API_EXTERN const wasm_extern_t* wasm_memory_as_extern_const(const wasm_memory_t*);

WASM_API_EXTERN const wasm_func_t* wasm_extern_as_func_const(const wasm_extern_t*);
WASM_API_EXTERN const wasm_global_t* wasm_extern_as_global_const(const wasm_extern_t*);
WASM_API_EXTERN const wasm_table_t* wasm_extern_as_table_const(const wasm_extern_t*);
WASM_API_EXTERN const wasm_memory_t* wasm_extern_as_memory_const(const wasm_extern_t*);
	 */
	void wasm_extern_vec_delete(Pointer externVecPointer);

	Pointer wasm_func_as_extern(Pointer func);

	Pointer wasm_extern_as_func(Pointer extern);

	/*
// Module Instances

WASM_DECLARE_REF(instance)

WASM_API_EXTERN own wasm_instance_t* wasm_instance_new(
  wasm_store_t*, const wasm_module_t*, const wasm_extern_vec_t* imports,
  own wasm_trap_t**
);

WASM_API_EXTERN void wasm_instance_exports(const wasm_instance_t*, own wasm_extern_vec_t* out);
	 */
	void wasm_instance_delete(Pointer instancePointer);

	Pointer wasm_instance_new(wasm_store_t_pointer storePointer, Pointer modulePointer, wasm_extern_vec_t imports, Pointer trapPointerPointer);

	void wasm_instance_exports(Pointer instancePointer, wasm_extern_vec_t out);

	/*
// Function Types construction short-hands

static inline own wasm_functype_t* wasm_functype_new_0_0(void) {
  wasm_valtype_vec_t params, results;
  wasm_valtype_vec_new_empty(&params);
  wasm_valtype_vec_new_empty(&results);
  return wasm_functype_new(&params, &results);
}
	 */
	default Pointer wasm_functype_new_0_0() {
		wasm_valtype_vec_t params = new wasm_valtype_vec_t(Runtime.getSystemRuntime());
		wasm_valtype_vec_t results = new wasm_valtype_vec_t(Runtime.getSystemRuntime());
		wasm_valtype_vec_new_empty(params);
		wasm_valtype_vec_new_empty(results);
		return wasm_functype_new(params, results);
	}
}
