// Locale support -*- C++ -*-

// Copyright (C) 1997, 1998, 1999, 2007 Free Software Foundation, Inc.
//
// This file is part of the GNU ISO C++ Library.  This library is free
// software; you can redistribute it and/or modify it under the
// terms of the GNU General Public License as published by the
// Free Software Foundation; either version 2, or (at your option)
// any later version.

// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.

// You should have received a copy of the GNU General Public License along
// with this library; see the file COPYING.  If not, write to the Free
// Software Foundation, 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301,
// USA.

// As a special exception, you may use this file as part of a free software
// library without restriction.  Specifically, if other files instantiate
// templates or use macros or inline functions from this file, or you compile
// this file and link it with other files to produce an executable, this
// file does not by itself cause the resulting executable to be covered by
// the GNU General Public License.  This exception does not however
// invalidate any other reasons why the executable file might be covered by
// the GNU General Public License.

//
// ISO C++ 14882: 22.1  Locales
//

//  We don't use the C-locale masks defined in mingw/include/ctype.h
//  because those masks do not conform to the requirements of 22.2.1.
//  In particular, a separate 'print' bitmask does not exist (isprint(c)
//  relies on a combination of flags) and the  '_ALPHA' mask is also a
//  combination of simple bitmasks.  Thus, we define libstdc++-specific
//  masks here, based on the generic masks, and the corresponding
//  classic_table in ctype_noninline.h.

_GLIBCXX_BEGIN_NAMESPACE(std)

  /// @brief  Base class for ctype.
  struct ctype_base
  {
    // Non-standard typedefs.
    typedef const int* 		__to_type;

    // NB: Offsets into ctype<char>::_M_table force a particular size
    // on the mask type. Because of this, we don't use an enum.
    typedef unsigned short 	mask;   
    static const mask upper	= 1 << 0;
    static const mask lower	= 1 << 1;
    static const mask alpha	= 1 << 2;
    static const mask digit	= 1 << 3;
    static const mask xdigit	= 1 << 4;
    static const mask space	= 1 << 5;
    static const mask print	= 1 << 6;
    static const mask graph	= (1 << 2) | (1 << 3) | (1 << 9);  // alnum|punct
    static const mask cntrl	= 1 << 8;
    static const mask punct 	= 1 << 9;
    static const mask alnum	= (1 << 2) | (1 << 3);  // alpha|digit
  };

_GLIBCXX_END_NAMESPACE
