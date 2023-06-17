package jp.hisano.wasm_c_api.tools

import java.io.File

val methods: MutableList<String> = mutableListOf()

val classes: MutableMap<String, String> = mutableMapOf()

fun WASM_DECLARE_OWN(name: String) {
    classes["wasm_${name}_t_pointer"] = """
        package jp.hisano.wasm_c_api.jna;
        
        import com.sun.jna.PointerType;
        
        public final class wasm_${name}_t_pointer extends PointerType {
        }
        
    """.trimIndent()

    methods += "void wasm_${name}_delete(wasm_${name}_t_pointer ${name}Pointer);"
}

fun WASM_DECLARE_VEC(name: String, ptr_or_none: String = "") {
    classes["wasm_${name}_vec_t"] = """
        package jp.hisano.wasm_c_api.jna;
        
        public final class wasm_${name}_vec_t extends wasm_vec_t {
        }
        
    """.trimIndent()

    methods += "void wasm_${name}_vec_new_empty(wasm_${name}_vec_t out);"
    methods += "void wasm_${name}_vec_new_uninitialized(wasm_${name}_vec_t* out, size_t size);"
    methods += "void wasm_${name}_vec_new(wasm_${name}_vec_t out, size_t size, wasm_${name}_t[] data);"
    methods += "void wasm_${name}_vec_copy(wasm_${name}_vec_t out, wasm_${name}_vec_t source);"
    methods += "void wasm_${name}_vec_delete(wasm_${name}_vec_t ${name});"
}

fun WASM_DECLARE_TYPE(name: String) {
    WASM_DECLARE_OWN(name)
    WASM_DECLARE_VEC(name, "*")

    methods += "wasm_${name}_t wasm_${name}_copy(wasm_${name}_t ${name});\n"
}

fun main() {
    WASM_DECLARE_VEC("byte")

    WASM_DECLARE_OWN("config")
    methods += "wasm_config_t wasm_config_new();\n"

    WASM_DECLARE_OWN("engine")

    methods += "wasm_engine_t_pointer wasm_engine_new();"
    methods += "wasm_engine_t_pointer wasm_engine_new_with_config(wasm_config_t_pointer configPointer);"

    WASM_DECLARE_OWN("store")

    methods += "wasm_store_t_pointer wasm_store_new(wasm_engine_t_pointer enginePointer);"

    WASM_DECLARE_TYPE("valtype")

    WASM_DECLARE_TYPE("functype")

    methods += "wasm_functype_t_pointer wasm_functype_new(wasm_valtype_vec_t params, wasm_valtype_vec_t results);"

    methods += "wasm_valtype_vec_t wasm_functype_params(wasm_functype_t_pointer functypePointer);"
    methods += "wasm_valtype_vec_t wasm_functype_results(wasm_functype_t_pointer functypePointer);"

    WASM_DECLARE_TYPE("globaltype")

//    WASM_API_EXTERN own wasm_globaltype_t* wasm_globaltype_new(own wasm_valtype_t*, wasm_mutability_t);
//
//    WASM_API_EXTERN const wasm_valtype_t* wasm_globaltype_content(const wasm_globaltype_t*);
//    WASM_API_EXTERN wasm_mutability_t wasm_globaltype_mutability(const wasm_globaltype_t*);

    WASM_DECLARE_TYPE("tabletype")

//    WASM_API_EXTERN own wasm_tabletype_t* wasm_tabletype_new(own wasm_valtype_t*, const wasm_limits_t*);
//
//    WASM_API_EXTERN const wasm_valtype_t* wasm_tabletype_element(const wasm_tabletype_t*);
//    WASM_API_EXTERN const wasm_limits_t* wasm_tabletype_limits(const wasm_tabletype_t*);
// Memory Types

    WASM_DECLARE_TYPE("memorytype")

//    WASM_API_EXTERN own wasm_memorytype_t* wasm_memorytype_new(const wasm_limits_t*);
//
//    WASM_API_EXTERN const wasm_limits_t* wasm_memorytype_limits(const wasm_memorytype_t*);

    WASM_DECLARE_TYPE("externtype")

    WASM_DECLARE_TYPE("importtype")

    WASM_DECLARE_TYPE("exporttype")

    WASM_DECLARE_VEC("val")

//    WASM_DECLARE_SHARABLE_REF(module)

//    WASM_DECLARE_REF(func)

    val directory = File("src/main/java/jp/hisano/wasm_c_api/jna")

    classes.forEach { (name, content) -> File(directory, "$name.java").writeText(content) }
}
