package jp.hisano.wasm_c_api.jna;

import com.sun.jna.Pointer;

public final class wasm_valtype_vec_t extends wasm_pointer_vec_t<wasm_valtype_t_pointer> {
    public wasm_valtype_vec_t() {
    }

    public wasm_valtype_vec_t(wasm_valtype_t_pointer[] dataElements) {
        super(dataElements);
    }

    @Override
    wasm_valtype_t_pointer toDataElement(Pointer pointer) {
        return new wasm_valtype_t_pointer(pointer);
    }
}
