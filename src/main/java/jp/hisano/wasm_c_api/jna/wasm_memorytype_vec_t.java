package jp.hisano.wasm_c_api.jna;

import com.sun.jna.Pointer;

public final class wasm_memorytype_vec_t extends wasm_pointer_vec_t<wasm_memorytype_t_pointer> {
    public wasm_memorytype_vec_t() {
    }

    public wasm_memorytype_vec_t(wasm_memorytype_t_pointer[] dataElements) {
        super(dataElements);
    }

    @Override
    wasm_memorytype_t_pointer toDataElement(Pointer pointer) {
        return new wasm_memorytype_t_pointer(pointer);
    }
}
