const BASE = import.meta.env.VITE_API_BASE || '';

async function getJson(path) {
  const res = await fetch(`${BASE}${path}`);
  if (!res.ok) throw new Error(`Request failed: ${res.status}`);
  return res.json();
}

export const getRates = () => getJson('/api/rates');
export const getPromises = () => getJson('/api/promises');
export const getReels = () => getJson('/api/reels');
export const getContent = () => getJson('/api/content');
