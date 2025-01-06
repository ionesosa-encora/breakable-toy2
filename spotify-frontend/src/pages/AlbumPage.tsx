import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import AlbumHeader from '../components/AlbumHeader';
import TrackList from '../components/TrackList';

interface Album {
  name: string;
  imageUrl: string;
  releaseDate: string;
  totalTracks: number;
  totalDuration: string;
  artistName: string;
}

interface Track {
  id: string;
  name: string;
  duration_ms: number;
  trackNumber: number;
}

const AlbumPage: React.FC = () => {
  const { id } = useParams<{ id: string }>();

  const [album, setAlbum] = useState<Album | null>(null);
  const [tracks, setTracks] = useState<Track[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchAlbumData = async () => {
      try {
        setLoading(true);
        setError(null);

        // Fetch album details
        const albumResponse = await fetch(`/api/albums/${id}`);
        if (!albumResponse.ok) throw new Error('Error fetching album data');
        const albumData = await albumResponse.json();

        // Set album details
        setAlbum({
          name: albumData.name,
          imageUrl: albumData.images?.[0]?.url || '', // Safe access to images
          releaseDate: albumData.release_date,
          totalTracks: albumData.total_tracks,
          totalDuration: formatTotalDuration(albumData.tracks.items), // Use tracks.items
          artistName: albumData.artists?.[0]?.name || 'Unknown Artist', // Safe access to artists
        });

        // Set tracks
        setTracks(
          albumData.tracks.items.map((track: any, index: number) => ({
            id: track.id,
            name: track.name,
            duration_ms: track.duration_ms,
            trackNumber: index + 1, // Track order
          }))
        );
      } catch (err: any) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchAlbumData();
  }, [id]);

  // Format total duration from tracks
  const formatTotalDuration = (tracks: any[]): string => {
    const totalMs = tracks.reduce((sum, track) => sum + track.duration_ms, 0);
    const minutes = Math.floor(totalMs / 60000);
    const seconds = Math.floor((totalMs % 60000) / 1000);
    return `${minutes} min ${seconds < 10 ? '0' : ''}${seconds} sec`;
  };

  if (loading) return <p>Loading...</p>;
  if (error) return <p>Error: {error}</p>;

  return (
    <div className="album-page">
      {album && (
        <AlbumHeader
          name={album.name}
          imageUrl={album.imageUrl}
          releaseDate={album.releaseDate}
          totalTracks={album.totalTracks}
          totalDuration={album.totalDuration}
          artistName={album.artistName}
        />
      )}
      <TrackList tracks={tracks} />
    </div>
  );
};

export default AlbumPage;
