const API_URL = import.meta.env.VITE_API_URL;
console.log("Environment mode: " + import.meta.env.MODE);
console.log("Using API URL:", API_URL);

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

  const content = await response.json();

  // NOTE - for debugging purposes, we log the API URL and response status
  if (!response.ok) {
    const errorLogMessage = `API Error: ${userErrorMessage} : "${content.message}" `;
    if (import.meta.env.MODE === "development") console.error(errorLogMessage);
    throw new Error(userErrorMessage);
  }

  return content;
};
