import {fileURLToPath, URL} from "node:url";

import {defineConfig} from "vite";
import vue from "@vitejs/plugin-vue";
import vueJsx from "@vitejs/plugin-vue-jsx";
import AutoImport from "unplugin-auto-import/vite";
import Components from "unplugin-vue-components/vite";
import {ElementPlusResolver} from "unplugin-vue-components/resolvers";

// https://vitejs.dev/config/
export default defineConfig({
    plugins: [
        vue(),
        vueJsx(),
        AutoImport({
            resolvers: [ElementPlusResolver()],
        }),
        Components({
            resolvers: [ElementPlusResolver()],
        }),
    ],
    server: {
        // 监听的主机（前端项目的启动地址）
        host: '127.0.0.1',
        // 监听的端口（前端项目的启动端口）
        port: 65000,
        // 配置代理
        proxy: {
            '/gateway': {
                // 后端服务地址
                target: 'http://127.0.0.1:65301',
                changeOrigin: true,
                // rewrite: path => path.replace(/^\/gateway/,'')
            }
        }
    },
    resolve: {
        alias: {
            "@": fileURLToPath(new URL("./src", import.meta.url)),
        },
    }
});
