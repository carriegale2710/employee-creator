"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
require("@testing-library/jest-dom");
var react_1 = require("@testing-library/react");
var vitest_1 = require("vitest");
// Cleanup after each test to avoid memory leaks
(0, vitest_1.afterEach)(function () {
    (0, react_1.cleanup)();
});
