package jp.hisano.wasm_c_api.jna;

import com.sun.jna.Callback;
import com.sun.jna.Library;
import com.sun.jna.platform.unix.LibCAPI.size_t;
import com.sun.jna.ptr.PointerByReference;

public interface WasmCApi extends Library {
    void wasm_byte_vec_new_empty(wasm_byte_vec_t out);
    void wasm_byte_vec_new_uninitialized(wasm_byte_vec_t out, size_t size);
    void wasm_byte_vec_copy(wasm_byte_vec_t out, wasm_byte_vec_t source);
    void wasm_byte_vec_delete(wasm_byte_vec_t target);
    
    void wasm_config_delete(wasm_config_t_pointer configPointer);
    wasm_config_t_pointer wasm_config_new();
    
    void wasm_engine_delete(wasm_engine_t_pointer enginePointer);
    wasm_engine_t_pointer wasm_engine_new();
    wasm_engine_t_pointer wasm_engine_new_with_config(wasm_config_t_pointer configPointer);
    
    void wasm_store_delete(wasm_store_t_pointer storePointer);
    wasm_store_t_pointer wasm_store_new(wasm_engine_t_pointer enginePointer);
    
    void wasm_valtype_delete(wasm_valtype_t_pointer valtypePointer);
    void wasm_valtype_vec_new_empty(wasm_valtype_vec_t out);
    void wasm_valtype_vec_new_uninitialized(wasm_valtype_vec_t out, size_t size);
    void wasm_valtype_vec_copy(wasm_valtype_vec_t out, wasm_valtype_vec_t source);
    void wasm_valtype_vec_delete(wasm_valtype_vec_t target);
    wasm_valtype_t_pointer wasm_valtype_copy(wasm_valtype_t_pointer valtype);
    
    void wasm_functype_delete(wasm_functype_t_pointer functypePointer);
    void wasm_functype_vec_new_empty(wasm_functype_vec_t out);
    void wasm_functype_vec_new_uninitialized(wasm_functype_vec_t out, size_t size);
    void wasm_functype_vec_copy(wasm_functype_vec_t out, wasm_functype_vec_t source);
    void wasm_functype_vec_delete(wasm_functype_vec_t target);
    wasm_functype_t_pointer wasm_functype_copy(wasm_functype_t_pointer functype);
    wasm_functype_t_pointer wasm_functype_new(wasm_valtype_vec_t params, wasm_valtype_vec_t results);
    wasm_valtype_vec_t wasm_functype_params(wasm_functype_t_pointer functypePointer);
    wasm_valtype_vec_t wasm_functype_results(wasm_functype_t_pointer functypePointer);
    
    void wasm_globaltype_delete(wasm_globaltype_t_pointer globaltypePointer);
    void wasm_globaltype_vec_new_empty(wasm_globaltype_vec_t out);
    void wasm_globaltype_vec_new_uninitialized(wasm_globaltype_vec_t out, size_t size);
    void wasm_globaltype_vec_copy(wasm_globaltype_vec_t out, wasm_globaltype_vec_t source);
    void wasm_globaltype_vec_delete(wasm_globaltype_vec_t target);
    wasm_globaltype_t_pointer wasm_globaltype_copy(wasm_globaltype_t_pointer globaltype);
    
    void wasm_tabletype_delete(wasm_tabletype_t_pointer tabletypePointer);
    void wasm_tabletype_vec_new_empty(wasm_tabletype_vec_t out);
    void wasm_tabletype_vec_new_uninitialized(wasm_tabletype_vec_t out, size_t size);
    void wasm_tabletype_vec_copy(wasm_tabletype_vec_t out, wasm_tabletype_vec_t source);
    void wasm_tabletype_vec_delete(wasm_tabletype_vec_t target);
    wasm_tabletype_t_pointer wasm_tabletype_copy(wasm_tabletype_t_pointer tabletype);
    
    void wasm_memorytype_delete(wasm_memorytype_t_pointer memorytypePointer);
    void wasm_memorytype_vec_new_empty(wasm_memorytype_vec_t out);
    void wasm_memorytype_vec_new_uninitialized(wasm_memorytype_vec_t out, size_t size);
    void wasm_memorytype_vec_copy(wasm_memorytype_vec_t out, wasm_memorytype_vec_t source);
    void wasm_memorytype_vec_delete(wasm_memorytype_vec_t target);
    wasm_memorytype_t_pointer wasm_memorytype_copy(wasm_memorytype_t_pointer memorytype);
    
    void wasm_externtype_delete(wasm_externtype_t_pointer externtypePointer);
    void wasm_externtype_vec_new_empty(wasm_externtype_vec_t out);
    void wasm_externtype_vec_new_uninitialized(wasm_externtype_vec_t out, size_t size);
    void wasm_externtype_vec_copy(wasm_externtype_vec_t out, wasm_externtype_vec_t source);
    void wasm_externtype_vec_delete(wasm_externtype_vec_t target);
    wasm_externtype_t_pointer wasm_externtype_copy(wasm_externtype_t_pointer externtype);
    
    void wasm_importtype_delete(wasm_importtype_t_pointer importtypePointer);
    void wasm_importtype_vec_new_empty(wasm_importtype_vec_t out);
    void wasm_importtype_vec_new_uninitialized(wasm_importtype_vec_t out, size_t size);
    void wasm_importtype_vec_copy(wasm_importtype_vec_t out, wasm_importtype_vec_t source);
    void wasm_importtype_vec_delete(wasm_importtype_vec_t target);
    wasm_importtype_t_pointer wasm_importtype_copy(wasm_importtype_t_pointer importtype);
    
    void wasm_exporttype_delete(wasm_exporttype_t_pointer exporttypePointer);
    void wasm_exporttype_vec_new_empty(wasm_exporttype_vec_t out);
    void wasm_exporttype_vec_new_uninitialized(wasm_exporttype_vec_t out, size_t size);
    void wasm_exporttype_vec_copy(wasm_exporttype_vec_t out, wasm_exporttype_vec_t source);
    void wasm_exporttype_vec_delete(wasm_exporttype_vec_t target);
    wasm_exporttype_t_pointer wasm_exporttype_copy(wasm_exporttype_t_pointer exporttype);
    
    void wasm_val_vec_new_empty(wasm_val_vec_t out);
    void wasm_val_vec_new_uninitialized(wasm_val_vec_t out, size_t size);
    void wasm_val_vec_copy(wasm_val_vec_t out, wasm_val_vec_t source);
    void wasm_val_vec_delete(wasm_val_vec_t target);
    
    void wasm_module_delete(wasm_module_t_pointer modulePointer);
    void wasm_shared_module_delete(wasm_shared_module_t_pointer shared_modulePointer);
    wasm_module_t_pointer wasm_module_new(wasm_store_t_pointer storePointer, wasm_byte_vec_t binary);
    boolean wasm_module_validate(wasm_store_t_pointer storePointer, wasm_byte_vec_t binary);
    void wasm_module_imports(wasm_module_t_pointer modulePointer, wasm_importtype_vec_t out);
    void wasm_module_exports(wasm_module_t_pointer modulePointer, wasm_exporttype_vec_t out);
    void wasm_module_serialize(wasm_module_t_pointer modulePointer, wasm_byte_vec_t out);
    wasm_module_t_pointer wasm_module_deserialize(wasm_store_t_pointer storePointer, wasm_byte_vec_t source);
    
    void wasm_func_delete(wasm_func_t_pointer funcPointer);
    wasm_func_t_pointer wasm_func_new(wasm_store_t_pointer storePointer, wasm_functype_t_pointer functypePointer, wasm_func_callback_t funcCallback);
    wasm_trap_t_pointer wasm_func_call(wasm_func_t_pointer funcPointer, wasm_val_vec_t args, wasm_val_vec_t results);
    
    void wasm_extern_delete(wasm_extern_t_pointer externPointer);
    void wasm_extern_vec_new_empty(wasm_extern_vec_t out);
    void wasm_extern_vec_new_uninitialized(wasm_extern_vec_t out, size_t size);
    void wasm_extern_vec_copy(wasm_extern_vec_t out, wasm_extern_vec_t source);
    void wasm_extern_vec_delete(wasm_extern_vec_t target);
    wasm_extern_t_pointer wasm_func_as_extern(wasm_func_t_pointer funcPointer);
    wasm_func_t_pointer wasm_extern_as_func(wasm_extern_t_pointer extern);
    
    void wasm_instance_delete(wasm_instance_t_pointer instancePointer);
    wasm_instance_t_pointer wasm_instance_new(wasm_store_t_pointer storePointer, wasm_module_t_pointer modulePointer, wasm_extern_vec_t imports, PointerByReference trapPointerPointer);
    void wasm_instance_exports(wasm_instance_t_pointer instancePointer, wasm_extern_vec_t out);

    default wasm_functype_t_pointer wasm_functype_new_0_0() {
        wasm_valtype_vec_t params = new wasm_valtype_vec_t();
        wasm_valtype_vec_t results = new wasm_valtype_vec_t();
        wasm_valtype_vec_new_empty(params);
        wasm_valtype_vec_new_empty(results);
        return wasm_functype_new(params, results);
    }
    
    interface wasm_func_callback_t extends Callback {
        wasm_trap_t_pointer invoke(wasm_val_vec_t args, wasm_val_vec_t results);
    }
}
