package jp.hisano.wasm_c_api.jna;

import com.sun.jna.Pointer;

public final class wasm_globaltype_vec_t extends wasm_pointer_vec_t<wasm_globaltype_t_pointer> {
    public wasm_globaltype_vec_t() {
    }

    public wasm_globaltype_vec_t(wasm_globaltype_t_pointer[] dataElements) {
        super(dataElements);
    }

    @Override
    wasm_globaltype_t_pointer toDataElement(Pointer pointer) {
        return new wasm_globaltype_t_pointer(pointer);
    }
}
