import React from 'react';

interface AlbumHeaderProps {
  name: string;
  imageUrl: string;
  releaseDate: string;
  totalTracks: number;
  totalDuration: string;
  artistName: string;
}

const formatDate = (dateString: string): string => {
  const date = new Date(dateString);
  return new Intl.DateTimeFormat('en-US', { day: '2-digit', month: 'long', year: 'numeric' }).format(date);
};

const AlbumHeader: React.FC<AlbumHeaderProps> = ({ 
  name, 
  imageUrl, 
  releaseDate, 
  totalTracks, 
  totalDuration, 
  artistName 
}) => {
  return (
    <div className="album-header">
      <div className="album-header__image-container">
        <img
          src={imageUrl || 'https://via.placeholder.com/300'}
          alt={name}
          className="album-header__image"
        />
      </div>
      <div className="album-header__info">
        <h1 className="album-header__name">{name}</h1>
        <p className="album-header__details">
          {formatDate(releaseDate)} • {totalTracks} songs • {totalDuration}
        </p>
        <p className="album-header__artist">By {artistName}</p>
      </div>
    </div>
  );
};

export default AlbumHeader;
