import { API_URL } from "/e-commerce/client/src/js/utils/constants.js";

export const fetchData = async (endpoint, method, inputData, errorMessage) => {
  try {
    const options = {
      method: method,
      headers: {
        'Content-Type': 'application/json'
      }
    };

    // Rutas que requieren autenticación
    const authRoutes = ['cart', 'cart/items', 'orders'];

    if (authRoutes.some(route => endpoint.startsWith(route))) {
      const token = localStorage.getItem("userSession");
      options.headers['Authorization'] = `Bearer ${token}`;
    }

    if (method !== 'GET' && method !== 'HEAD' && inputData) {
      options.body = JSON.stringify(inputData);
    }

    const response = await fetch(`${API_URL}/${endpoint}`, options);

    if (response.status === 204) {
      return { success: true };
    }

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
