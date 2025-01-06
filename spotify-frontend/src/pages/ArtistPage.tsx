import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import ArtistHeader from '../components/ArtistHeader';
import TopTracks from '../components/TopTracks';
import AlbumsGrid from '../components/AlbumsGrid';

interface Artist {
  id: string;
  name: string;
  imageUrl: string;
  followers: number;
  genres: string[];
}

interface Track {
  id: string;
  name: string;
  duration_ms: number;
  albumImageUrl: string;
}

interface Album {
  id: string;
  name: string;
  imageUrl: string;
  release_date: string;
}

const ArtistPage: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  console.log('Artist ID:', id);

  const [artist, setArtist] = useState<Artist | null>(null);
  const [topTracks, setTopTracks] = useState<Track[]>([]);
  const [albums, setAlbums] = useState<Album[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchArtistData = async () => {
      try {
        setLoading(true);
        setError(null);

        // Fetch información del artista
        const artistResponse = await fetch(`/api/artists/${id}`);
        if (!artistResponse.ok) throw new Error('Error fetching artist info');
        const artistData = await artistResponse.json();
        setArtist({
          id: artistData.id,
          name: artistData.name,
          imageUrl: artistData.images[0]?.url || '',
          followers: artistData.followers.total,
          genres: artistData.genres || [],
        });
        console.log("artisResponse: ", artistData.id)

        // Fetch canciones populares
        const topTracksResponse = await fetch(`/api/artists/${id}/top-tracks`);
        if (!topTracksResponse.ok) throw new Error('Error fetching top tracks');
        const topTracksData = await topTracksResponse.json();
        setTopTracks(
          (topTracksData.tracks || [])
            .slice(0, 5) // Limitar los resultados a los primeros 5 tracks
            .map((track: any) => ({
              id: track.id,
              name: track.name,
              duration_ms: track.duration_ms,
              albumImageUrl: track.album?.images[2]?.url || track.album?.images[1]?.url || '',
            }))
        );

        // Fetch álbumes
        const albumsResponse = await fetch(`/api/artists/${id}/albums`);
        if (!albumsResponse.ok) throw new Error('Error fetching albums');
        const albumsData = await albumsResponse.json();
        setAlbums(
          (albumsData || []).map((album: any) => ({
            id: album.id,
            name: album.name,
            imageUrl: album.images[0]?.url || '',
            release_date: album.release_date,
          }))
        );
      } catch (err: any) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchArtistData();
  }, [id]);

  if (loading) return <p>Loading...</p>;
  if (error) return <p>Error: {error}</p>;

  return (
    <>
      {artist && (
        <ArtistHeader
          name={artist.name}
          imageUrl={artist.imageUrl}
          followers={artist.followers}
          genres={artist.genres}
        />
      )}
      <TopTracks tracks={topTracks} />
      <AlbumsGrid albums={albums} />
    </>
  );
};

export default ArtistPage;