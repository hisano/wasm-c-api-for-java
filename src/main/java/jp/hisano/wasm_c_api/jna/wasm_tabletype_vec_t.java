package jp.hisano.wasm_c_api.jna;

import com.sun.jna.Pointer;

public final class wasm_tabletype_vec_t extends wasm_pointer_vec_t<wasm_tabletype_t_pointer> {
    public wasm_tabletype_vec_t() {
    }

    public wasm_tabletype_vec_t(wasm_tabletype_t_pointer[] dataElements) {
        super(dataElements);
    }

    @Override
    wasm_tabletype_t_pointer toDataElement(Pointer pointer) {
        return new wasm_tabletype_t_pointer(pointer);
    }
}
