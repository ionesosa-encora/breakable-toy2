import React from 'react';
import AlbumCard from './AlbumCard';

interface Album {
  id: string;
  name: string;
  imageUrl: string;
  release_date: string;
}

interface AlbumsGridProps {
  albums: Album[];
}

const AlbumsGrid: React.FC<AlbumsGridProps> = ({ albums }) => {
  return (
    <div className="albums-grid">
      <h2>Albums</h2>
      <div className="albums-grid__container">
        {albums.map((album) => (
          <AlbumCard
            key={album.id}
            id={album.id}
            name={album.name}
            imageUrl={album.imageUrl}
            releaseDate={album.release_date}
          />
        ))}
      </div>
    </div>
  );
};

export default AlbumsGrid;
