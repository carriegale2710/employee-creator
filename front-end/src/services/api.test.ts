import { afterAll, afterEach, beforeEach, describe, expect, it, vi } from "vitest";
import { apiCall } from "./api";
import { deleteEmployee } from "./employees";

describe("apiCall", () => {
  const originalFetch = global.fetch;
  const consoleErrorSpy = vi
    .spyOn(console, "error")
    .mockImplementation(() => undefined);
  const consoleDebugSpy = vi
    .spyOn(console, "debug")
    .mockImplementation(() => undefined);

  beforeEach(() => {
    global.fetch = vi.fn();
  });

  afterEach(() => {
    global.fetch = originalFetch;
    vi.clearAllMocks();
  });

  afterAll(() => {
    consoleErrorSpy.mockRestore();
    consoleDebugSpy.mockRestore();
  });

  it("returns parsed JSON for successful responses", async () => {
    const responseBody = { id: 1, name: "Test Employee" };
    vi.mocked(global.fetch).mockResolvedValue(
      new Response(JSON.stringify(responseBody), {
        status: 200,
        headers: { "Content-Type": "application/json" },
      })
    );

    await expect(apiCall("/employees")).resolves.toEqual(responseBody);
  });

  it("includes JSON error details in thrown errors", async () => {
    vi.mocked(global.fetch).mockResolvedValue(
      new Response(
        JSON.stringify({
          message: "Validation failed",
          errors: [{ defaultMessage: "Email is invalid" }],
        }),
        {
          status: 400,
          headers: { "Content-Type": "application/json" },
        }
      )
    );

    await expect(apiCall("/employees", "Failed to save employee")).rejects.toThrow(
      "Failed to save employee (status 400) - Validation failed Email is invalid"
    );
  });

  it("handles plain text error responses without throwing parse errors", async () => {
    vi.mocked(global.fetch).mockResolvedValue(
      new Response("Service unavailable", {
        status: 503,
        headers: { "Content-Type": "text/plain" },
      })
    );

    await expect(apiCall("/employees", "Failed to load employees")).rejects.toThrow(
      "Failed to load employees (status 503) - Service unavailable"
    );
  });

  it("handles DELETE errors with text responses consistently", async () => {
    vi.mocked(global.fetch).mockResolvedValue(
      new Response("Delete failed", {
        status: 500,
        headers: { "Content-Type": "text/plain" },
      })
    );

    await expect(
      apiCall("/employees/1", "Failed to delete employee", {
        method: "DELETE",
      })
    ).rejects.toThrow("Failed to delete employee (status 500) - Delete failed");
  });

  it("propagates DELETE service failures instead of returning success", async () => {
    vi.mocked(global.fetch).mockResolvedValue(
      new Response("Delete failed", {
        status: 500,
        headers: { "Content-Type": "text/plain" },
      })
    );

    await expect(deleteEmployee(1)).rejects.toThrow(
      "Failed to delete employee 1 (status 500) - Delete failed"
    );
  });

  it("returns success for DELETE service calls with empty responses", async () => {
    vi.mocked(global.fetch).mockResolvedValue(new Response(null, { status: 204 }));

    await expect(deleteEmployee(1)).resolves.toBe(true);
  });
});
