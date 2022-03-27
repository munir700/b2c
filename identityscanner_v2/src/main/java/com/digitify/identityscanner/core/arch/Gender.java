/**
 * Java parser for the MRZ records, as specified by the ICAO organization.
 * Copyright (C) 2011 Innovatrics s.r.o.
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package com.digitify.identityscanner.core.arch;

/**
 * MRZ sex.
 * @author Martin Vysny
 */
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
@Deprecated(message = "We are using Uqudo SDK instead of Identity Scanner", level = DeprecationLevel.WARNING)
public enum Gender {

    Male('M'),
    Female('F'),
    Unspecified('X');

    /**
     * The MRZ character.
     */
    public final char mrz;
    
    Gender(char mrz) {
        this.mrz = mrz;
    }
    
    public static Gender fromMrz(char sex) {
        switch (sex) {
            case 'M':
                return Male;
            case 'F':
                return Female;
            case '<':
            case 'X':
                return Unspecified;
            default:
                throw new RuntimeException("Invalid MRZ sex character: " + sex);
        }
    }
}
