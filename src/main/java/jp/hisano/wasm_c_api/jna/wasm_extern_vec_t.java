package jp.hisano.wasm_c_api.jna;

import com.sun.jna.Pointer;

public final class wasm_extern_vec_t extends wasm_pointer_vec_t<wasm_extern_t_pointer> {
    public wasm_extern_vec_t() {
    }

    public wasm_extern_vec_t(wasm_extern_t_pointer[] dataElements) {
        super(dataElements);
    }

    @Override
    wasm_extern_t_pointer toDataElement(Pointer pointer) {
        return new wasm_extern_t_pointer(pointer);
    }
}
