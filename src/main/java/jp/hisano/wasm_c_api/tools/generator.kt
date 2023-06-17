package jp.hisano.wasm_c_api.tools

import java.io.File

val methods: MutableList<String> = mutableListOf()

val classes: MutableMap<String, String> = mutableMapOf()

fun WASM_DECLARE_OWN(name: String) {
    classes["wasm_${name}_t_pointer"] = """
        package jp.hisano.wasm_c_api.jna;
        
        import com.sun.jna.Pointer;
        import com.sun.jna.PointerType;
        
        public final class wasm_${name}_t_pointer extends PointerType {
            public wasm_${name}_t_pointer() {
            }
            
            public wasm_${name}_t_pointer(Pointer pointer) {
                super(pointer);
            }
        }
        
    """.trimIndent()

    methods += "void wasm_${name}_delete(wasm_${name}_t_pointer ${name}Pointer);"
}

fun WASM_DECLARE_VEC(name: String, ptr_or_none: String = "") {
    if (ptr_or_none == "") {
        classes["wasm_${name}_vec_t"] = """
            package jp.hisano.wasm_c_api.jna;
            
            public final class wasm_${name}_vec_t extends wasm_vec_t {
            }
            
        """.trimIndent()
    } else {
        classes["wasm_${name}_vec_t"] = """
            package jp.hisano.wasm_c_api.jna;
            
            import com.sun.jna.Pointer;
            
            public final class wasm_${name}_vec_t extends wasm_pointer_vec_t<wasm_${name}_t_pointer> {
                public wasm_${name}_vec_t() {
                }
            
                public wasm_${name}_vec_t(wasm_${name}_t_pointer[] dataElements) {
                    super(dataElements);
                }
            
                @Override
                wasm_${name}_t_pointer toDataElement(Pointer pointer) {
                    return new wasm_${name}_t_pointer(pointer);
                }
            }
            
        """.trimIndent()
    }

    methods += "void wasm_${name}_vec_new_empty(wasm_${name}_vec_t out);"
    methods += "void wasm_${name}_vec_new_uninitialized(wasm_${name}_vec_t out, size_t size);"
//    methods += "void wasm_${name}_vec_new(wasm_${name}_vec_t out, size_t size, wasm_${name}_t[] data);"
    methods += "void wasm_${name}_vec_copy(wasm_${name}_vec_t out, wasm_${name}_vec_t source);"
    methods += "void wasm_${name}_vec_delete(wasm_${name}_vec_t target);"
}

fun WASM_DECLARE_TYPE(name: String) {
    WASM_DECLARE_OWN(name)
    WASM_DECLARE_VEC(name, "*")

    methods += "wasm_${name}_t_pointer wasm_${name}_copy(wasm_${name}_t_pointer ${name});"
}

fun WASM_DECLARE_REF_BASE(name: String) {
    WASM_DECLARE_OWN(name)
//    WASM_API_EXTERN own wasm_##name##_t* wasm_##name##_copy(const wasm_##name##_t*); \
//    WASM_API_EXTERN bool wasm_##name##_same(const wasm_##name##_t*, const wasm_##name##_t*); \
//    WASM_API_EXTERN void* wasm_##name##_get_host_info(const wasm_##name##_t*); \
//    WASM_API_EXTERN void wasm_##name##_set_host_info(wasm_##name##_t*, void*); \
//    WASM_API_EXTERN void wasm_##name##_set_host_info_with_finalizer(wasm_##name##_t*, void*, void (*)(void*));
}

fun WASM_DECLARE_REF(name: String) {
    WASM_DECLARE_REF_BASE(name)
//    WASM_API_EXTERN wasm_ref_t* wasm_##name##_as_ref(wasm_##name##_t*); \
//    WASM_API_EXTERN wasm_##name##_t* wasm_ref_as_##name(wasm_ref_t*); \
//    WASM_API_EXTERN const wasm_ref_t* wasm_##name##_as_ref_const(const wasm_##name##_t*); \
//    WASM_API_EXTERN const wasm_##name##_t* wasm_ref_as_##name##_const(const wasm_ref_t*);
}

fun WASM_DECLARE_SHARABLE_REF(name: String) {
    WASM_DECLARE_REF(name)
    WASM_DECLARE_OWN("shared_$name")
//    WASM_API_EXTERN own wasm_shared_##name##_t* wasm_##name##_share(const wasm_##name##_t*); \
//    WASM_API_EXTERN own wasm_##name##_t* wasm_##name##_obtain(wasm_store_t*, const wasm_shared_##name##_t*);
}

fun main() {
    WASM_DECLARE_VEC("byte")

    addSeparator()

    WASM_DECLARE_OWN("config")
    methods += "wasm_config_t_pointer wasm_config_new();"

    addSeparator()

    WASM_DECLARE_OWN("engine")
    methods += "wasm_engine_t_pointer wasm_engine_new();"
    methods += "wasm_engine_t_pointer wasm_engine_new_with_config(wasm_config_t_pointer configPointer);"

    addSeparator()

    WASM_DECLARE_OWN("store")
    methods += "wasm_store_t_pointer wasm_store_new(wasm_engine_t_pointer enginePointer);"

    addSeparator()

    WASM_DECLARE_TYPE("valtype")

    addSeparator()

    WASM_DECLARE_TYPE("functype")
    methods += "wasm_functype_t_pointer wasm_functype_new(wasm_valtype_vec_t params, wasm_valtype_vec_t results);"
    methods += "wasm_valtype_vec_t wasm_functype_params(wasm_functype_t_pointer functypePointer);"
    methods += "wasm_valtype_vec_t wasm_functype_results(wasm_functype_t_pointer functypePointer);"

    addSeparator()

    WASM_DECLARE_TYPE("globaltype")

//    WASM_API_EXTERN own wasm_globaltype_t* wasm_globaltype_new(own wasm_valtype_t*, wasm_mutability_t);
//
//    WASM_API_EXTERN const wasm_valtype_t* wasm_globaltype_content(const wasm_globaltype_t*);
//    WASM_API_EXTERN wasm_mutability_t wasm_globaltype_mutability(const wasm_globaltype_t*);

    addSeparator()

    WASM_DECLARE_TYPE("tabletype")

//    WASM_API_EXTERN own wasm_tabletype_t* wasm_tabletype_new(own wasm_valtype_t*, const wasm_limits_t*);
//
//    WASM_API_EXTERN const wasm_valtype_t* wasm_tabletype_element(const wasm_tabletype_t*);
//    WASM_API_EXTERN const wasm_limits_t* wasm_tabletype_limits(const wasm_tabletype_t*);
// Memory Types

    addSeparator()

    WASM_DECLARE_TYPE("memorytype")

    addSeparator()

//    WASM_API_EXTERN own wasm_memorytype_t* wasm_memorytype_new(const wasm_limits_t*);
//
//    WASM_API_EXTERN const wasm_limits_t* wasm_memorytype_limits(const wasm_memorytype_t*);

    WASM_DECLARE_TYPE("externtype")

    addSeparator()

    WASM_DECLARE_TYPE("importtype")

    addSeparator()

    WASM_DECLARE_TYPE("exporttype")

    addSeparator()

    WASM_DECLARE_VEC("val")

    addSeparator()

    WASM_DECLARE_SHARABLE_REF("module")
    methods += "wasm_module_t_pointer wasm_module_new(wasm_store_t_pointer storePointer, wasm_byte_vec_t binary);"
    methods += "boolean wasm_module_validate(wasm_store_t_pointer storePointer, wasm_byte_vec_t binary);"
    methods += "void wasm_module_imports(wasm_module_t_pointer modulePointer, wasm_importtype_vec_t out);"
    methods += "void wasm_module_exports(wasm_module_t_pointer modulePointer, wasm_exporttype_vec_t out);"
    methods += "void wasm_module_serialize(wasm_module_t_pointer modulePointer, wasm_byte_vec_t out);"
    methods += "wasm_module_t_pointer wasm_module_deserialize(wasm_store_t_pointer storePointer, wasm_byte_vec_t source);"

    addSeparator()

    WASM_DECLARE_REF("func")
    methods += "wasm_func_t_pointer wasm_func_new(wasm_store_t_pointer storePointer, wasm_functype_t_pointer functypePointer, wasm_func_callback_t funcCallback);"
    methods += "wasm_trap_t_pointer wasm_func_call(wasm_func_t_pointer funcPointer, wasm_val_vec_t args, wasm_val_vec_t results);"

    addSeparator()

    WASM_DECLARE_REF("extern")
    WASM_DECLARE_VEC("extern", "*")

    methods += "wasm_extern_t_pointer wasm_func_as_extern(wasm_func_t_pointer funcPointer);"
    methods += "wasm_func_t_pointer wasm_extern_as_func(wasm_extern_t_pointer extern);"

    addSeparator()

    WASM_DECLARE_REF("instance")
    methods += "wasm_instance_t_pointer wasm_instance_new(wasm_store_t_pointer storePointer, wasm_module_t_pointer modulePointer, wasm_extern_vec_t imports, PointerByReference trapPointerPointer);"
    methods += "void wasm_instance_exports(wasm_instance_t_pointer instancePointer, wasm_extern_vec_t out);"

    val directory = File("src/main/java/jp/hisano/wasm_c_api/jna")

    classes.forEach { (name, content) -> File(directory, "$name.java").writeText(content) }

    var api = """
        package jp.hisano.wasm_c_api.jna;
        
        import com.sun.jna.Callback;
        import com.sun.jna.Library;
        import com.sun.jna.platform.unix.LibCAPI.size_t;
        import com.sun.jna.ptr.PointerByReference;
        
        public interface WasmCApi extends Library {
        
    """.trimIndent()

    methods.forEach { api += "    ${it}\n" }

    api += """
        
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

    """.trimIndent()

    File(directory, "WasmCApi.java").writeText(api)
}

fun addSeparator() {
    methods += ""
}
