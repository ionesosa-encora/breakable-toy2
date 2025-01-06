import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

interface SearchInputProps {
  placeholder?: string;
}

const SearchInput: React.FC<SearchInputProps> = ({ placeholder = "Search for artists, albums, and tracks..." }) => {
  const [query, setQuery] = useState('');
  const navigate = useNavigate();

  const handleSearch = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault(); // Evitar el comportamiento por defecto del formulario
    if (query.trim() !== '') {
      navigate(`/search?q=${encodeURIComponent(query)}`);
    }
  };

  return (
    <form className="search-input" onSubmit={handleSearch}>
      <input
        type="text"
        className="search-input__field"
        placeholder={placeholder}
        value={query}
        onChange={(e) => setQuery(e.target.value)}
      />
      <button type="submit" className="search-input__button">Search</button>
    </form>
  );
};

export default SearchInput;
