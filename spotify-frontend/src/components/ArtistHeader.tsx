import React from 'react';

interface ArtistHeaderProps {
  name: string;
  imageUrl: string | null;
  followers: number;
  genres: string[];
}

const ArtistHeader: React.FC<ArtistHeaderProps> = ({ name, imageUrl, followers, genres }) => {
  const displayedGenres = genres.slice(0, 2).join(', '); 

  return (
    <div className="artist-header">
      <div className="artist-header__image-container">
        <img
          src={imageUrl || 'https://via.placeholder.com/300'} 
          alt={name}
          className="artist-header__image"
        />
      </div>
      <div className="artist-header__info">
        <h1 className="artist-header__name">{name}</h1>
        <p className="artist-header__followers">
          {followers.toLocaleString()} Followers
        </p>
        {displayedGenres && (
          <p className="artist-header__genres">Genres: {displayedGenres}</p>
        )}
      </div>
    </div>
  );
};

export default ArtistHeader;
