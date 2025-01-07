export const getCookie = (cookieName: string): string | null => {
    const cookie = document.cookie
      .split('; ')
      .find(row => row.startsWith(`${cookieName}=`));
    return cookie ? cookie.split('=')[1] : null;
  };
  