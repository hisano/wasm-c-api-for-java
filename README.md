# WebAssembly C API binding for Java

# How to use

See [a sample application](src/test/java/jp/hisano/wasm_c_api/jna/example/Hello.java).

# Bundled WebAssembly Runtime

- [Wasmtime v9.0.4](https://github.com/bytecodealliance/wasmtime/releases/tag/v9.0.4)
- [Wasmber v3.3.0](https://github.com/wasmerio/wasmer/releases/tag/v3.3.0)
- You can use any WebAssembly runtime and version because this library is just a binding.

# Implementation Notes

* Covers [WebAssembly C API](https://github.com/WebAssembly/wasm-c-api)
* Implemented with [JNA](https://github.com/java-native-access/jna)

## License

Apache License Version 2.0
