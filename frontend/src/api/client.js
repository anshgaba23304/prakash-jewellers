const RAW_BASE = import.meta.env.VITE_API_BASE || '';
const BASE = RAW_BASE.replace(/\/+$/, '');

function joinUrl(base, path) {
  if (!base) return path;
  return `${base}/${String(path).replace(/^\/+/, '')}`;
}

async function getJson(path) {
  const res = await fetch(joinUrl(BASE, path));
  if (!res.ok) throw new Error(`Request failed: ${res.status}`);
  return res.json();
}

export const getRates = () => getJson('/api/rates');
export const getPromises = () => getJson('/api/promises');
export const getReels = () => getJson('/api/reels');
export const getContent = () => getJson('/api/content');
