import { describe, expect, it, vi, beforeEach } from "vitest";
import { deleteEmployee } from "./employees";
import { apiCall } from "./api";

vi.mock("./api", () => ({
  apiCall: vi.fn(),
}));

describe("deleteEmployee", () => {
  beforeEach(() => {
    vi.clearAllMocks();
  });

  it("returns the delete request promise result", async () => {
    vi.mocked(apiCall).mockResolvedValueOnce(undefined);

    await expect(deleteEmployee(42)).resolves.toBeUndefined();
    expect(apiCall).toHaveBeenCalledWith(
      "/employees/42",
      "Failed to delete employee42",
      {
        method: "DELETE",
      }
    );
  });

  it("rejects when the delete request fails", async () => {
    vi.mocked(apiCall).mockRejectedValueOnce(new Error("delete failed"));

    await expect(deleteEmployee(42)).rejects.toThrow("delete failed");
  });
});
