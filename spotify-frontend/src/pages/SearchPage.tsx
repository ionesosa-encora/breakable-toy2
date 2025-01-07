import React, { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";
import SearchInput from "../components/SearchInput";
import ArtistGrid from "../components/ArtistGrid";
import AlbumsGrid from "../components/AlbumsGrid";
import TrackSearch from "../components/TrackSearch";

const SearchPage: React.FC = () => {
  const location = useLocation();
  const query = new URLSearchParams(location.search).get("q") || "";

  const [artists, setArtists] = useState([]);
  const [albums, setAlbums] = useState([]);
  const [tracks, setTracks] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchSearchResults = async () => {
      try {
        const response = await fetch(
          `/api/search?q=${encodeURIComponent(query)}&type=artist,album,track`
        );
        if (response.ok) {
          const data = await response.json();

          // Procesar artistas
          setArtists(
            (data.artists.items || []).slice(0, 10).map((artist: any) => ({
              id: artist.id,
              name: artist.name,
              imageUrl: artist.images?.[0]?.url || "", 
              followers: artist.followers?.total || 0,
              genres: artist.genres || [],
            }))
          );

          // Procesar álbumes
          setAlbums(
            (data.albums.items || []).slice(0, 10).map((album: any) => ({
              id: album.id,
              name: album.name,
              imageUrl: album.images?.[0]?.url || "", 
              release_date: album.release_date,
            }))
          );

          // Procesar tracks
          setTracks(
            (data.tracks.items || []).slice(0, 10).map((track: any) => ({
              id: track.id,
              name: track.name,
              duration_ms: track.duration_ms,
              albumImageUrl: track.album?.images?.[0]?.url || "", 
              albumName: track.album?.name || "Unknown Album",
              artistName: track.artists?.[0]?.name || "Unknown Artist",
            }))
          );
        } else {
          console.error("Error fetching search results:", response.status);
        }
      } catch (error) {
        console.error("Error fetching search results:", error);
      } finally {
        setLoading(false);
      }
    };

    if (query) {
      fetchSearchResults();
    }
  }, [query]);

  if (loading) {
    return <p>Loading...</p>;
  }

  return (
    <div className="search-page">
      {/* Componente de búsqueda */}
      <SearchInput placeholder="Search for artists, albums, or tracks..." />
  
      <h1>Search Results for "{query}"</h1>
  
      {/* Mostrar artistas */}
      {artists.length > 0 && (
        <>
          <h2>Artists</h2>
          <ArtistGrid artists={artists} />
        </>
      )}
  
      {/* Mostrar álbumes */}
      {albums.length > 0 && <AlbumsGrid albums={albums} />}
  
      {/* Mostrar tracks */}
      {tracks.length > 0 && <TrackSearch tracks={tracks} />}
  
      {/* Mensaje de sin resultados */}
      {artists.length === 0 && albums.length === 0 && tracks.length === 0 && (
        <p>No results found.</p>
      )}
    </div>
  );
  
};

export default SearchPage;
