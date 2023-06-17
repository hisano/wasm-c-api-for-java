package jp.hisano.wasm_c_api.jna;

import com.sun.jna.Pointer;
import com.sun.jna.PointerType;

public final class wasm_shared_module_t_pointer extends PointerType {
    public wasm_shared_module_t_pointer() {
    }
    
    public wasm_shared_module_t_pointer(Pointer pointer) {
        super(pointer);
    }
}
