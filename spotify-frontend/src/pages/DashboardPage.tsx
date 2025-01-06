import React, { useEffect, useState } from 'react';
import ArtistGrid from '../components/ArtistGrid';
import SearchInput from '../components/SearchInput';

interface Artist {
  id: string;
  name: string;
  imageUrl: string;
  followers: number;
  popularity: number;
  genres: string[];
}

const DashboardPage: React.FC = () => {
  const [artists, setArtists] = useState<Artist[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchTopArtists = async () => {
      try {
        const response = await fetch('api/spotify/me/top/artists', {
          credentials: 'include',
        });
        if (response.ok) {
          const data = await response.json();
          const formattedArtists = data.items.map((artist: any) => ({
            id: artist.id,
            name: artist.name,
            imageUrl: artist.images?.[0]?.url || '',
            followers: artist.followers?.total || 0,
            popularity: artist.popularity,
            genres: artist.genres || [],
          }));
          setArtists(formattedArtists);
        } else {
          console.error('Error fetching top artists:', response.status);
        }
      } catch (error) {
        console.error('Error fetching top artists:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchTopArtists();
  }, []);

  if (loading) {
    return <p>Loading...</p>;
  }

  return (
    <div>
      {/* Componente de búsqueda */}
      <SearchInput />
  
      <h1>Your Top Artists</h1>
  
      {artists.length > 0 ? (
        <ArtistGrid artists={artists} />
      ) : (
        <p>No top artists found.</p>
      )}
    </div>
  );
};

export default DashboardPage;
