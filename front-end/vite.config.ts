import { defineConfig } from "vitest/config";
import react from "@vitejs/plugin-react";

// https://vite.dev/config/
export default defineConfig({
  plugins: [react()],
  test: {
    globals: true, //no need for imports in every file
    environment: "jsdom",
    setupFiles: "./config/test-setup.ts",
  },
});
