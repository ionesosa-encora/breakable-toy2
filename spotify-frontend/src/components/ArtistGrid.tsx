import React from 'react';
import TopArtistCard from './ArtistCard';

interface Artist {
  id: string;
  name: string;
  imageUrl: string | null;
  followers: number;
  popularity: number;
  genres: string[];
}

interface ArtistGridProps {
  artists: Artist[];
}

const ArtistGrid: React.FC<ArtistGridProps> = ({ artists }) => {
  return (
    <div className="grid">
      {artists.map(artist => (
        <TopArtistCard
          key={artist.id}
          id={artist.id}
          name={artist.name}
          imageUrl={artist.imageUrl}
          followers={artist.followers}
          popularity={artist.popularity}
          genres={artist.genres}
        />
      ))}
    </div>
  );
};

export default ArtistGrid;
