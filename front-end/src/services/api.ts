const API_URL = import.meta.env.VITE_API_URL;
console.debug("Environment mode: " + import.meta.env.MODE);
console.debug("Using API URL:", API_URL);

export const apiCall = async <T>(
  endpoint: string,
  userErrorMessage: string = "API call failed",
  options?: RequestInit
): Promise<T> => {
  const response = await fetch(`${API_URL}${endpoint}`, {
    headers: {
      "Content-Type": "application/json",
      ...options?.headers,
    },
    ...options,
  });

  const content = options?.method === "DELETE" ? null : await response.json();

  // NOTE - Gracefully handle errors
  if (!response.ok) {
    const errors = `${content?.errors?.map(
      (error: { defaultMessage: string }) => `${error.defaultMessage} `
    )}`;

    const errorLogMessage = `API Error: ${userErrorMessage}
    Status ${response.status}
    Endpoint: ${API_URL}${endpoint}
    Message: "${content?.message}"
    Errors: ${errors}`;

    if (import.meta.env.MODE === "development") console.error(errorLogMessage);
    throw new Error(
      `${userErrorMessage} - ${errors}` //display to user
    );
  }

  return content;
};
