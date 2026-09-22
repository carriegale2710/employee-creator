const API_URL = import.meta.env.VITE_API_URL;
console.debug("Environment mode: " + import.meta.env.MODE);
console.debug("Using API URL:", API_URL);

type ApiErrorResponse = {
  message?: string;
  errors?: Array<{ defaultMessage?: string }>;
};

const parseResponseContent = async (
  response: Response
): Promise<unknown | string | null> => {
  const rawContent = await response.text();

  if (!rawContent) return null;

  try {
    return JSON.parse(rawContent);
  } catch {
    return rawContent;
  }
};

const getErrorDetails = (content: unknown): string[] => {
  if (typeof content === "string") return [content];

  if (!content || typeof content !== "object") return [];

  const { message, errors } = content as ApiErrorResponse;

  return [
    ...(message ? [message] : []),
    ...(errors
      ?.map((error) => error.defaultMessage)
      .filter((message): message is string => Boolean(message)) ?? []),
  ];
};

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

  const content = await parseResponseContent(response);

  // NOTE - Gracefully handle errors
  if (!response.ok) {
    const errorDetails = getErrorDetails(content).join(" ");
    const formattedErrorDetails = errorDetails
      ? `\n    Details: "${errorDetails}"`
      : "";

    const errorLogMessage = `API Error: ${userErrorMessage}
    Status ${response.status}
    Endpoint: ${API_URL}${endpoint}
    Raw response: ${typeof content === "string" ? content : JSON.stringify(content)}${formattedErrorDetails}`;

    if (import.meta.env.MODE === "development") console.error(errorLogMessage);
    throw new Error(
      `${userErrorMessage} (status ${response.status})${errorDetails ? ` - ${errorDetails}` : ""}` //display to user
    );
  }

  return content as T;
};
