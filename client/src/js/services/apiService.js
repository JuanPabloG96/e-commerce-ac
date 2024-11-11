import { API_URL } from "/e-commerce/client/src/js/utils/constants.js";

// manejo general de solicitudes
export const fetchData = async (endpoint, method, inputData, errorMessage) => {
  try {
    const options = {
      method: method,
      headers: {
        'Content-Type': 'application/json',
      }
    };

    if (method !== 'GET' && method !== 'HEAD' && inputData) {
      options.body = JSON.stringify(inputData);
    }

    const response = await fetch(`${API_URL}/${endpoint}`, options);
    const data = await response.json();

    if (!response.ok) {
      throw new Error(data.message || `Error de respuesta de la API - ${response.status}`);
    }

    return data;
  } catch (error) {
    console.error(errorMessage, error);
    throw error;
  }
};

