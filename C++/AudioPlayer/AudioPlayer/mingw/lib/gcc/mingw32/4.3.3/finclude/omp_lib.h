!  Copyright (C) 2005 Free Software Foundation, Inc.
!  Contributed by Jakub Jelinek <jakub@redhat.com>.

!  This file is part of the GNU OpenMP Library (libgomp).

!  Libgomp is free software; you can redistribute it and/or modify it
!  under the terms of the GNU Lesser General Public License as published by
!  the Free Software Foundation; either version 2.1 of the License, or
!  (at your option) any later version.

!  Libgomp is distributed in the hope that it will be useful, but WITHOUT ANY
!  WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
!  FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for
!  more details.

!  You should have received a copy of the GNU Lesser General Public License
!  along with libgomp; see the file COPYING.LIB.  If not, write to the
!  Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
!  MA 02110-1301, USA.  */

!  As a special exception, if you link this library with other files, some
!  of which are compiled with GCC, to produce an executable, this library
!  does not by itself cause the resulting executable to be covered by the
!  GNU General Public License.  This exception does not however invalidate
!  any other reasons why the executable file might be covered by the GNU
!  General Public License.

      integer omp_lock_kind, omp_nest_lock_kind, openmp_version
      parameter (omp_lock_kind = 4)
      parameter (omp_nest_lock_kind = 8)
      parameter (openmp_version = 200505)

      external omp_init_lock, omp_init_nest_lock
      external omp_destroy_lock, omp_destroy_nest_lock
      external omp_set_lock, omp_set_nest_lock
      external omp_unset_lock, omp_unset_nest_lock
      external omp_set_dynamic, omp_set_nested
      external omp_set_num_threads

      external omp_get_dynamic, omp_get_nested
      logical*4 omp_get_dynamic, omp_get_nested
      external omp_test_lock, omp_in_parallel
      logical*4 omp_test_lock, omp_in_parallel

      external omp_get_max_threads, omp_get_num_procs
      integer*4 omp_get_max_threads, omp_get_num_procs
      external omp_get_num_threads, omp_get_thread_num
      integer*4 omp_get_num_threads, omp_get_thread_num
      external omp_test_nest_lock
      integer*4 omp_test_nest_lock

      external omp_get_wtick, omp_get_wtime
      double precision omp_get_wtick, omp_get_wtime
