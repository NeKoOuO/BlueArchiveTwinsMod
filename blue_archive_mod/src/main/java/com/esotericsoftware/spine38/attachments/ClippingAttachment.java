/******************************************************************************
 * Spine Runtimes License Agreement
 * Last updated January 1, 2020. Replaces all prior versions.
 *
 * Copyright (c) 2013-2020, Esoteric Software LLC
 *
 * Integration of the Spine Runtimes into software or otherwise creating
 * derivative works of the Spine Runtimes is permitted under the terms and
 * conditions of Section 2 of the Spine Editor License Agreement:
 * http://esotericsoftware.com/spine-editor-license
 *
 * Otherwise, it is permitted to integrate the Spine Runtimes into software
 * or otherwise create derivative works of the Spine Runtimes (collectively,
 * "Products"), provided that each user of the Products must obtain their own
 * Spine Editor license and redistribution of the Products in any form must
 * include this license and copyright notice.
 *
 * THE SPINE RUNTIMES ARE PROVIDED BY ESOTERIC SOFTWARE LLC "AS IS" AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL ESOTERIC SOFTWARE LLC BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES,
 * BUSINESS INTERRUPTION, OR LOSS OF USE, DATA, OR PROFITS) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THE SPINE RUNTIMES, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *****************************************************************************/

package com.esotericsoftware.spine38.attachments;

import com.badlogic.gdx.graphics.Color;
import com.esotericsoftware.spine38.SlotData;

/** An attachment with vertices that make up a polygon used for clipping the rendering of other attachments. */
public class ClippingAttachment extends VertexAttachment {
	SlotData endSlot;

	// Nonessential.
	final Color color = new Color(0.2275f, 0.2275f, 0.8078f, 1); // ce3a3aff

	public ClippingAttachment (String name) {
		super(name);
	}

	/** Clipping is performed between the clipping polygon's slot and the end slot. Returns -1 if clipping is done until the end of
	 * the skeleton's rendering. */
	public SlotData getEndSlot () {
		return endSlot;
	}

	public void setEndSlot (SlotData endSlot) {
		this.endSlot = endSlot;
	}

	/** The color of the clipping polygon as it was in Spine. Available only when nonessential data was exported. Clipping polygons
	 * are not usually rendered at runtime. */
	public Color getColor () {
		return color;
	}

	public Attachment copy () {
		ClippingAttachment copy = new ClippingAttachment(name);
		copyTo(copy);
		copy.endSlot = endSlot;
		copy.color.set(color);
		return copy;
	}
}
