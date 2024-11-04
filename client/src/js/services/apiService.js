export const fetchProducts = async (API_URL) => {
  try {
    const response = await fetch(`${API_URL}/products`);
    if (!response.ok) {
      throw new Error('Error en la respuesta de la API');
    }
    const data = await response.json();
    return data;
  } catch (error) {
    console.error('Error al fetch de productos:', error);
    throw error; // O maneja el error de otra manera
  }
};

// Función para crear un usuario
export const createUser = async (API_URL, userData) => {
  try {
    const response = await fetch(`${API_URL}/users`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(userData),
    });
    if (!response.ok) {
      throw new Error('Error de respuesta de la API');
    }
    const data = await response.json();
    return data;
  } catch (error) {
    console.error('Error al crear el usuario:', error);
    throw error; // O maneja el error de otra manera
  }
};

// Función para iniciar sesión
export const loginUser = async (API_URL, userID) => {
  try {
    const response = await fetch(`${API_URL}/login`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(userID),
    });
    if (!response.ok) {
      throw new Error('Error de respuesta de la API');
    }
    const data = await response.json();
    return data;
  } catch (error) {
    console.error('Error al iniciar sesión:', error);
    throw error; // O maneja el error de otra manera
  }
};