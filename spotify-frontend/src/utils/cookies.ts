export const getCookie = (cookieName: string): string | null => {
    const cookie = document.cookie
      .split('; ')
      .find(row => row.startsWith(`${cookieName}=`));
      console.log("getcookie :")
      console.log(cookieName)
      console.log(cookie)
      console.log(document.cookie)
    return cookie ? cookie.split('=')[1] : null;
  };
  